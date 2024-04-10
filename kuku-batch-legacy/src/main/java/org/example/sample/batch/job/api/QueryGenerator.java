package org.example.sample.batch.job.api;

import org.example.sample.batch.domain.ProductVO;
import org.example.sample.batch.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryGenerator {

    public static ProductVO[] getProductList(DataSource datasource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        List<ProductVO> productList = jdbcTemplate.query("select type from product group by type", new ProductRowMapper(){
            @Override
            public ProductVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ProductVO.builder().type(rs.getString("type")).build();
            }
        });
        return productList.toArray(new ProductVO[]{});
    }

    public static Map<String, Object> getParameterForQuery(String parameter, String value) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(parameter, value);
        return parameters;
    }
}
