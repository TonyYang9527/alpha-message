package com.dcf.iqunxing.message2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.model.MsgTemplateVo;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.ListTemplateRequest;
import com.dcf.iqunxing.message2.response.ListMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.service.ITemplateService;
import com.dcf.iqunxing.message2.service.internal.union.TemplateUnionService;
import com.dcf.iqunxing.message2.util.LogUtils;

@Service("templateService")
public class TemplateServiceImpl implements ITemplateService {

    private static final String LOG_TITLE = "多模版查询请求服务";

    ILog log = LogManager.getLogger(TemplateServiceImpl.class);

    @Autowired
    protected TemplateUnionService templateUnionService;

    @Override
    public ListMsgTemplateResponse listMsgTemplate(ListTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "name", "state");
        LogUtils.info(this.getClass(), LOG_TITLE, " listMsgTemplate  request ", request, builder);
        ListMsgTemplateResponse resp = new ListMsgTemplateResponse();
        PageResult<MsgTemplateVo> result = templateUnionService.selectByExample(request);
        resp.setResult(result);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("查询成功");
        LogUtils.info(this.getClass(), LOG_TITLE, " listMsgTemplate  response ", resp, builder);
        return resp;
    }
}
