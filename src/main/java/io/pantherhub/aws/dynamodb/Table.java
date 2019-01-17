package io.pantherhub.aws.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;


public class Table {

    /**
     * Create a table with maximum number of strongly consistent reads and write consumed per second set to 10.
     * @param tableName
     *        Name of the table to create.
     * @return
     *        Action object with the create table execution results.
     */
    public Action create(String tableName) {
        return create(tableName, 10L, 10L);
    }

    /**
     * Create a table with maximum number of strongly consistent reads and write consumed per second.
     * @param tableName
     *        Name of the table to create.
     * @param readCapacityUnits
     *        The maximum number of strongly consistent reads consumed per second before DynamoDB returns a
     *        <code>ThrottlingException</code>. For more information, see <a href=
     *        "http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput"
     *        >Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB Developer Guide</i>.</p>
     *        <p>
     *        If read/write capacity mode is <code>PAY_PER_REQUEST</code> the value is set to 0.
     * @param writeCapacityUnits
     *        The maximum number of writes consumed per second before DynamoDB returns a
     *        <code>ThrottlingException</code>. For more information, see <a href=
     *        "http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput"
     *        >Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB Developer Guide</i>.
     *        </p>
     *        <p>
     *        If read/write capacity mode is <code>PAY_PER_REQUEST</code> the value is set to 0.
     * @return
     *        Action object with the create table execution results.
     */
    public Action create(String tableName, Long readCapacityUnits, Long writeCapacityUnits) {
        CreateTableRequest request = new CreateTableRequest()
                .withAttributeDefinitions(new AttributeDefinition(
                        "Name", ScalarAttributeType.S))
                .withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
                .withProvisionedThroughput(new ProvisionedThroughput(
                        readCapacityUnits, writeCapacityUnits))
                .withTableName(tableName);


        try {
            CreateTableResult result = this.client().createTable(request);
            return new Action(200, String.format("Successfully created table : %s", tableName), result);
        } catch (AmazonServiceException e) {
            return new Action(500, String.format("Unable to create table : %s", e.getErrorMessage()), null);
        }
    }

    /**
     * Update the given table with maximum number of strongly consistent reads and write consumed per second.
     * @param tableName
     *        Name of the table to create.
     * @param readCapacityUnits
     *        The maximum number of strongly consistent reads consumed per second before DynamoDB returns a
     *        <code>ThrottlingException</code>. For more information, see <a href=
     *        "http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput"
     *        >Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB Developer Guide</i>.</p>
     *        <p>
     *        If read/write capacity mode is <code>PAY_PER_REQUEST</code> the value is set to 0.
     * @param writeCapacityUnits
     *        The maximum number of writes consumed per second before DynamoDB returns a
     *        <code>ThrottlingException</code>. For more information, see <a href=
     *        "http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput"
     *        >Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB Developer Guide</i>.
     *        </p>
     *        <p>
     *        If read/write capacity mode is <code>PAY_PER_REQUEST</code> the value is set to 0.
     * @return
     *        Action object with the update table execution results.
     */
    public Action update(String tableName, Long readCapacityUnits, Long writeCapacityUnits) {
        ProvisionedThroughput tableThroughput = new ProvisionedThroughput(
                readCapacityUnits,
                writeCapacityUnits
        );

        try {
            return new Action(
                    200,
                    String.format("Successfully updated table : %s", tableName),
                    this.client().updateTable(tableName, tableThroughput)
            );
        } catch (AmazonServiceException e) {
            return new Action(
                    500,
                    String.format("Unable to update table : %s", e.getErrorMessage()),
                    null
            );
        }
    }

    /**
     * Delete the given table.
     * @param tableName
     *        Name of the table to delete.
     * @return
     *        Action object with the delete table  execution results.
     */
    public Action delete(String tableName) {

        try {
            return new Action(
                    200,
                    String.format("Successfully deleted table : %s", tableName),
                    this.client().deleteTable(tableName)
            );
        } catch (AmazonServiceException e) {
            return new Action(
                    500,
                    String.format("Unable to delete table : %s", e.getErrorMessage()),
                    null
            );
        }
    }

    private AmazonDynamoDB client() {
        AmazonDynamoDB ddb;
        if (!isLocalEnv()) {
            ddb = AmazonDynamoDBClientBuilder.defaultClient();
        } else {
            ddb = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                    "http://localhost:8000",
                                    "local"
                            )
                    )
                    .build();
        }
        return ddb;
    }

    protected Boolean isLocalEnv() {
    	return Boolean.valueOf(System.getProperty(this.getClass().getPackage().toString()));
    }

}

