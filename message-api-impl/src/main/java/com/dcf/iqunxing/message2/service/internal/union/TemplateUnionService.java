package com.dcf.iqunxing.message2.service.internal.union;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.TemplateUnionMapper;
import com.dcf.iqunxing.message2.entity.TemplateUnion;
import com.dcf.iqunxing.message2.entity.TemplateUnionExample;
import com.dcf.iqunxing.message2.model.MsgTemplateVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.page.Page;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.ListTemplateRequest;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;
import com.google.common.base.Strings;

@Service
public class TemplateUnionService {

    @Autowired
    protected TemplateUnionMapper templateUnionMapper;

    public PageResult<MsgTemplateVo> selectByExample(ListTemplateRequest request) {
        TemplateUnionExample example = new TemplateUnionExample();
        TemplateUnionExample.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(request.getName()))
            criteria.andNameLike("%" + request.getName() + "%");
        if (request.getState() != null)
            criteria.andStateEqualTo(request.getState().getValue());
        example.setOrderByClause(" created_time desc , updated_time  desc ");
        PageResult<MsgTemplateVo> result = new PageResult<MsgTemplateVo>();
        Page page = request.getPage();
        if (page != null) {
            if (page.isNeedTotalRecord()) {
                // 查询相应条件一共有几条
                int count = templateUnionMapper.countByExample(example);
                page.setTotalRecord(count);
            }
            // 分页计算
            example.setLimitStart(page.getStart());
            example.setLimitEnd(page.getPageSize());
        }
        List<TemplateUnion> templateUnions = null;
        if (page != null && page.isNeedTotalRecord() && page.getTotalRecord() == 0)
            templateUnions = new ArrayList<TemplateUnion>();
        else
            templateUnions = templateUnionMapper.selectByExample(example);

        List<MsgTemplateVo> templateUnionsVo = new ArrayList<MsgTemplateVo>();
        for (TemplateUnion union : templateUnions) {
            MsgTemplateVo vo = BeanMapper.map(union, MsgTemplateVo.class);
            vo.setMsgPriority(MsgPriority.fromValue(union.getPriority()));
            templateUnionsVo.add(vo);
        }
        result.setPage(page);
        result.setResult(templateUnionsVo);
        return result;
    }

}
