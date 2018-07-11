package com.dcf.iqunxing.message2.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;

// TODO: Auto-generated Javadoc
/**
 * The Class MailValid.
 */
@Component
public class MailValid {

    private static final String LOG_TITLE = "验证邮箱有效服务";

    ILog log = LogManager.getLogger(MailValid.class);

    /** The domain. */
    @Value("${email.host}")
    private String domain;

    /** 超时时间. */
    private static final int TIME_OUT = 6000;

    /** 睡眠时间. */
    private static int SLEEP_SECT = 50;

    /**
     * 验证邮箱是否存在 由于要读取IO，会造成线程阻塞.
     *
     * @param mailAddress
     *            要验证的邮箱
     * @return 邮箱是否可达
     */
    public boolean valid(String mailAddress) {
        LogUtils.info(this.getClass(), LOG_TITLE, "验证邮箱开始", mailAddress, getTagBuilder(mailAddress));
        boolean validFlag = false;
        boolean basicValidSuccess = basicValid(mailAddress);
        if (basicValidSuccess) {
            String mxHost = getMxHost(mailAddress);
            if (StringUtils.isNotEmpty(mxHost)) {
                Socket socket = null;
                BufferedReader reader = null;
                BufferedWriter writer = null;
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(mxHost, 25));
                    reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    if (connect(reader, mailAddress) && helo(reader, writer, mailAddress) && identity(reader, writer, mailAddress)
                            && rcpt(reader, writer, mailAddress)) {
                        validFlag = true;
                    }
                } catch (IOException | InterruptedException e) {
                    LogUtils.error(this.getClass(), LOG_TITLE, "连接到达邮件地址域名出错", mailAddress, e, getTagBuilder(mailAddress));
                } finally {
                    try {
                        reader.close();
                        writer.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "验证邮箱结束，结果为", validFlag, getTagBuilder(mailAddress));
        return validFlag;

    }

    /**
     * 对发送地址最基础的验证.
     *
     * @param mailAddress
     *            mailAddress
     * @return true, if successful
     */
    private boolean basicValid(String mailAddress) {
        if (StringUtils.isBlank(mailAddress) || StringUtils.isBlank(domain)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "邮箱地址为空", mailAddress, getTagBuilder(mailAddress));
            return false;
        }
        if (!StringUtils.contains(mailAddress, '@')) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "邮箱地址没有@符号", mailAddress, getTagBuilder(mailAddress));
            return false;
        }
        String host = getHost(mailAddress);
        if (host.equals(domain)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "邮箱地址host和邮件服务器域名相同", mailAddress, getTagBuilder(mailAddress));
            return false;
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "邮箱地址基础验证通过", mailAddress, getTagBuilder(mailAddress));
        return true;
    }

    /**
     * 获取到达邮箱的host.
     *
     * @param mailAddress
     *            mailAddress
     * @return the host
     */
    private String getHost(String mailAddress) {
        return mailAddress.substring(mailAddress.indexOf('@') + 1);
    }

    /**
     * 获取MXHost(邮件交换记录).
     *
     * @param mailAddress
     *            mailAddress
     * @return the mx host
     */
    private String getMxHost(String mailAddress) {
        String mxHost = null;
        String host = getHost(mailAddress);
        // 查找mx记录
        Record[] mxRecords;
        try {
            mxRecords = new Lookup(host, Type.MX).run();
            if (ArrayUtils.isNotEmpty(mxRecords)) {
                // 邮件服务器地址
                mxHost = ((MXRecord) mxRecords[0]).getTarget().toString();
                if (mxRecords.length > 1) { // 优先级排序
                    List<Record> arrRecords = new ArrayList<Record>();
                    Collections.addAll(arrRecords, mxRecords);
                    Collections.sort(arrRecords, new Comparator<Record>() {

                        public int compare(Record o1, Record o2) {
                            return new CompareToBuilder().append(((MXRecord) o1).getPriority(), ((MXRecord) o2).getPriority())
                                    .toComparison();
                        }

                    });
                    mxHost = ((MXRecord) arrRecords.get(0)).getTarget().toString();
                }
            }
        } catch (TextParseException e) {
            e.printStackTrace();
            mxHost = null;
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "获取到的mxHost", mxHost, getTagBuilder(mailAddress));
        return mxHost;
    }

    /**
     * 连接socket结果.
     *
     * @param socket
     *            the socket
     * @return true, if successful
     * @throws IOException
     * @throws InterruptedException
     */
    private boolean connect(BufferedReader reader, String mailAddress) throws IOException, InterruptedException {
        int rightCode = 220;
        String validType = "连接";
        return getResponseResult(reader, rightCode, validType, mailAddress);
    }

    /**
     * 握手socket结果.
     *
     * @param socket
     *            the socket
     * @return true, if successful
     * @throws IOException
     * @throws InterruptedException
     */
    private boolean helo(BufferedReader reader, BufferedWriter writer, String mailAddress)
            throws IOException, InterruptedException {
        int rightCode = 250;
        String validType = "握手";
        writer.write("HELO " + domain + "\r\n");
        writer.flush();
        return getResponseResult(reader, rightCode, validType, mailAddress);
    }

    /**
     * 身份socket结果.
     *
     * @param socket
     *            the socket
     * @return true, if successful
     * @throws IOException
     * @throws InterruptedException
     */
    private boolean identity(BufferedReader reader, BufferedWriter writer, String mailAddress)
            throws IOException, InterruptedException {
        int rightCode = 250;
        String validType = "身份";
        writer.write("MAIL FROM: <check@" + domain + ">\r\n");
        writer.flush();
        return getResponseResult(reader, rightCode, validType, mailAddress);
    }

    /**
     * 验证socket结果.
     *
     * @param socket
     *            the socket
     * @return true, if successful
     * @throws IOException
     * @throws InterruptedException
     */
    private boolean rcpt(BufferedReader reader, BufferedWriter writer, String mailAddress)
            throws IOException, InterruptedException {
        int rightCode = 250;
        String validType = "验证";
        writer.write("RCPT TO: <" + mailAddress + ">\r\n");
        writer.flush();
        return getResponseResult(reader, rightCode, validType, mailAddress);
    }

    /**
     * 获取服务器响应结果.
     *
     * @param reader
     *            the reader
     * @param rightCode
     *            the right code
     * @param validType
     *            the valid type
     * @param mailAddress
     *            the mail address
     * @return the response result
     * @throws InterruptedException
     *             the interrupted exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private boolean getResponseResult(BufferedReader reader, int rightCode, String validType, String mailAddress)
            throws InterruptedException, IOException {
        boolean result = false;
        int code = 0;
        for (int i = SLEEP_SECT; i < TIME_OUT; i += SLEEP_SECT) {
            Thread.sleep(SLEEP_SECT);
            if (reader.ready()) {
                String outline = reader.readLine();
                code = Integer.parseInt(outline.substring(0, 3));
                break;
            }
        }
        if (code == rightCode) {
            LogUtils.info(this.getClass(), LOG_TITLE, "发送邮件地址服务校验" + validType + "响应正确", mailAddress, getTagBuilder(mailAddress));
            result = true;
        } else {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送邮件地址服务校验" + validType + "响应错误(正确为" + rightCode + ")", code,
                    getTagBuilder(mailAddress));
        }
        return result;
    }

    /**
     * 构造Tagbuilder.
     *
     * @param mailAddress
     *            the mail address
     * @return the tag builder
     */
    public TagBuilder getTagBuilder(String mailAddress) {
        TagBuilder builder = TagBuilder.create();
        builder.append("mailAddress", mailAddress);
        return builder;
    }

}
