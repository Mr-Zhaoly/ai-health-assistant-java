package com.zly.controller;

import com.zly.common.vo.base.ResultT;
import com.zly.service.IMilvusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/milvus")
public class MilvusController {

    @Autowired
    private IMilvusService milvusService;

    @PostMapping("/init")
    public ResultT<Boolean> init() {
        return ResultT.success(milvusService.init());
    }
}
