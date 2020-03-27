package AAAAAAPracs.SwaggerUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bockey
 */
@Api(value = "商品名称", tags = "商品信息相关API", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class APITestController {

    @ApiOperation(value = "根据参数查询商品并分页展示", produces = "application/json")
    @GetMapping("/api")
    public String find(
            @ApiParam(value = "查询商品页数") @RequestParam(defaultValue = "1") int pageSize,
            @ApiParam(value = "查询商品每页展示数量" +
                    "") @RequestParam(defaultValue = "20") int pageNum) {

        return "test res";
    }
}
