package com.havaFun.producer;
import com.alibaba.dubbo.config.annotation.Service;
import com.havaFun.service.WmsUserService;
import com.haveFun.model.WmsUser;
import com.haveFun.service.WmsUserProducerService;
import org.springframework.stereotype.Component;
@Service
@Component
public class WmsUserProducerServiceImpl implements WmsUserProducerService {
    private WmsUserService service = WmsUserService.getInstance();

    public WmsUser findById(Integer id) {
        return service.findOne(id);
    }
}
