package com.cyber.demo.mapper;

import com.cyber.common.mybatis.annotation.DataColumn;
import com.cyber.common.mybatis.annotation.DataPermission;
import com.cyber.common.mybatis.core.mapper.BaseMapperPlus;
import com.cyber.demo.domain.entity.TestTree;
import com.cyber.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTree, TestTreeVo> {

}
