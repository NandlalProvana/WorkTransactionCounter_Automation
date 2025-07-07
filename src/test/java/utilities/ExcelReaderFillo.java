package utilities;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReaderFillo {

    private static final String EXCEL_PATH = "src/test/resources/testdata/TestData.xls"; // Adjust path as needed

    // 🔗 Get connection to the Excel file
    private static Connection getConnection() throws Exception {
        Fillo fillo = new Fillo();
        return fillo.getConnection(EXCEL_PATH);
    }

    // 📊 SELECT query - returns list of rows as Map<String, String>
    public static List<Map<String, String>> select(String query) {
        List<Map<String, String>> results = new ArrayList<>();
        Connection conn = null;
        Recordset rs = null;

        try {
            conn = getConnection();
            rs = conn.executeQuery(query);
            List<String> fields = rs.getFieldNames();

            while (rs.next()) {
                Map<String, String> row = new LinkedHashMap<>();
                for (String field : fields) {
                    row.put(field, rs.getField(field));
                }
                results.add(row);
            }
        } catch (Exception e) {
            System.err.println("❌ Fillo SELECT error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (Exception ignored) {
            }
        }

        return results;
    }

    // 🛠 Generic method for INSERT / UPDATE / DELETE
    public static boolean executeUpdate(String query) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.err.println("❌ Fillo UPDATE error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {
            }
        }
    }

    // 📌 Get a single cell value from a row based on a WHERE condition
    public static String getCellValue(String sheet, String whereColumn, String whereValue, String targetColumn) {
        String value = null;
        Connection conn = null;
        Recordset rs = null;

        try {
            conn = getConnection();
            String query = String.format("SELECT * FROM %s WHERE %s='%s'", sheet, whereColumn, whereValue);
            rs = conn.executeQuery(query);
            if (rs.next()) {
                value = rs.getField(targetColumn);
            }
        } catch (Exception e) {
            System.err.println("❌ Fillo getCellValue error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (Exception ignored) {
            }
        }

        return value;
    }

    // 🌐 Get environment-specific value (e.g., Password from QA or UAT column)
    public static String getEnvValue(String key, String envColumn) {
        String value = null;
        Connection conn = null;
        Recordset rs = null;

        try {
            conn = getConnection();
            String query = String.format("SELECT %s FROM EnvironmentConfig WHERE KEY='%s'", envColumn, key);
            rs = conn.executeQuery(query);
            if (rs.next()) {
                value = rs.getField(envColumn);
            }
        } catch (Exception e) {
            System.err.println("❌ Fillo getEnvValue error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (Exception ignored) {
            }
        }

        return value;
    }

    // 📝 Set/update a value into the environment column (QA / UAT) dynamically
    public static boolean setEnvValue(String key, String column, String value) {
        String query = String.format("UPDATE EnvironmentConfig SET %s='%s' WHERE KEY='%s'", column, value, key);
        return executeUpdate(query);
    }


    //Logic to set and update value of sop
    /*String sopName = "Automation SOP - " + System.currentTimeMillis();
    String envColumn = env.equalsIgnoreCase("QA") ? "QA" : "UAT";
ExcelReaderFillo.setEnvValue("LatestSOPName", envColumn, sopName);*/
}
