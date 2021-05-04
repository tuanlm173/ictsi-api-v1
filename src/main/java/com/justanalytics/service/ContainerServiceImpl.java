package com.justanalytics.service;

import com.justanalytics.repository.DataRepository;
import com.justanalytics.types.ContainerType;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContainerServiceImpl implements ContainerService {

    Logger logger = LoggerFactory.getLogger(ContainerServiceImpl.class);

    @Autowired
    private DataRepository dataRepository;

    private static final String API_EMPTY_CTR_QUERY = "SELECT TOP %s * FROM dbo.container c LEFT JOIN dbo.vessel_visit vv ON c.PlannedOBVisitUniqueKey = vv.UniqueKey";
    private static final String API_EXPORT_CTR_QUERY = "SELECT TOP %s * FROM dbo.container c LEFT JOIN dbo.bill_of_ladings bol on c.GoodsGkey = bol.Goods_UniqueKey LEFT JOIN dbo.vessel_visit vv on c.PlannedOBVisitUniqueKey = vv.UniqueKey";
    private static final String API_IMPORT_CTR_QUERY = "SELECT TOP %s * FROM dbo.container c LEFT JOIN dbo.bill_of_ladings bol on c.GoodsGkey = bol.Goods_UniqueKey LEFT JOIN dbo.vessel_visit vv on c.ActualIBVisitUniqueKey = vv.UniqueKey";
    private static final String API_SIMPLE_CTR_QUERY = "SELECT TOP %s * FROM dbo.container c LEFT JOIN dbo.bill_of_ladings bol ON c.GoodsGkey = bol.Goods_UniqueKey";

    private static final String BOL_CONDITION = "(c.BillofLadingNbr = '%s' OR bol.House_BLNbr = '%s')";
    private static final String CONTAINER_CONDITION = "('%s' IS NULL OR c.ContainerNbr = '%s')";
    private static final String DEFAULT_CONDITION = "1=1";

    private static final String COSMOS_QUERY = "SELECT TOP %s * FROM container_api c ";
    private static final String BOL_COSMOS_CONDITION = "(c.BillofLadingNbr = '%s' OR ARRAY_CONTAINS(c.BillOfLadings, {'House_BLNbr': '%s'}, true))";
    private static final String CONTAINER_COSMOS_CONDITION = "(c.ContainerNbr = '%s')";
    private static final String COSMOS_CONTAINER = "container_api";

    private StringBuilder buildSimpleContainerQuery(String query, String size) {
        StringBuilder queryBuilder = new StringBuilder();
        return queryBuilder.append(String.format(query, size));
    }

    @Override
    public List<Map<String, Object>> findContainerBol(
            String containerType, String containerName, String billOfLadingNbr, String size
    ) {
        StringBuilder queryBuilder = new StringBuilder();
        String bolFilter = "";
        String containerFilter = "";

        if (ContainerType.EXPORT.getContainerType().equalsIgnoreCase(containerType))
            queryBuilder = buildSimpleContainerQuery(API_EXPORT_CTR_QUERY, size);
        else if (ContainerType.IMPORT.getContainerType().equalsIgnoreCase(containerType))
            queryBuilder = buildSimpleContainerQuery(API_IMPORT_CTR_QUERY, size);
        else queryBuilder = buildSimpleContainerQuery(API_SIMPLE_CTR_QUERY, size);

        if (billOfLadingNbr != null && !billOfLadingNbr.isBlank())
            bolFilter = String.format(BOL_CONDITION, billOfLadingNbr, billOfLadingNbr);
        else bolFilter = DEFAULT_CONDITION;

        if (containerName != null && !containerName.isBlank())
            containerFilter = String.format(CONTAINER_CONDITION, containerName, containerName);
        else containerFilter = DEFAULT_CONDITION;

        queryBuilder.append(String.format(" WHERE %s AND %s", bolFilter, containerFilter));

        String sql = queryBuilder.toString();
        logger.info("Querying sql statement: {}", sql);

        return dataRepository.getData(sql);
    }

    @Override
    public List<JSONObject> findContainerBolCosmos(
            String containerName, String billOfLadingNbr, String size
    ) {
        StringBuilder queryBuilder = new StringBuilder();
        String bolFilter = "";
        String containerFilter = "";

        queryBuilder = buildSimpleContainerQuery(COSMOS_QUERY, size);

        if (billOfLadingNbr != null && !billOfLadingNbr.isBlank())
            bolFilter = String.format(BOL_COSMOS_CONDITION, billOfLadingNbr, billOfLadingNbr);
        else bolFilter = DEFAULT_CONDITION;

        if (containerName != null && !containerName.isBlank())
            containerFilter = String.format(CONTAINER_COSMOS_CONDITION, containerName);
        else containerFilter = DEFAULT_CONDITION;

        queryBuilder.append(String.format(" WHERE %s AND %s", bolFilter, containerFilter));

        String sql = queryBuilder.toString();

        logger.info("Cosmos SQL statement: {}", sql);
        return dataRepository.getSimpleDataFromCosmos(COSMOS_CONTAINER, sql);
    }

}
