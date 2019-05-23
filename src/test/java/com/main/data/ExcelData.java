package com.main.data;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ExcelData {

    /**
     * @param sheetName 表名
     * @param fileName  文件名
     * @return
     * @throws IOException
     * @throws BiffException
     */
    public static Object[][] getExcelData(String sheetName, String fileName) throws IOException, BiffException {
        Workbook workbook = Workbook.getWorkbook(new File(getPath(fileName)));
        Sheet sheet = workbook.getSheet(sheetName);
        int rows = sheet.getRows();
        int columns = sheet.getColumns();
        // data storage
        HashMap<String, String>[][] data = new HashMap[rows - 1][1];
        // init haspmap
        if (rows <= 1) return null;
        for (int i = 1; i < rows; i++) {
            // init data
            data[i - 1][0] = new HashMap<>();
            for (int j = 0; j < columns; j++) {
                data[i - 1][0].put(sheet.getCell(j, 0).getContents(), sheet.getCell(j, i).getContents());
            }
        }
        return data;
    }

    /**
     * Get the path of data excel
     *
     * @return
     * @throws java.io.IOException
     */
    private static String getPath(String fileName) throws IOException {
        File file = new File(".");
        return file.getCanonicalPath() + "\\src\\main\\resources\\" + fileName;
    }

    /**
     * update the status of testcase: 通过，未通过
     *
     * @param id
     * @param content
     * @param sheetName
     * @param fileName
     * @throws IOException
     * @throws BiffException
     * @throws WriteException
     */
    public static void updateTestCasePass(String id, String content, String sheetName, String fileName) throws IOException, BiffException, WriteException {
        File file = new File(getPath(fileName));
        Workbook wb = Workbook.getWorkbook(file);
        WritableWorkbook workbook = wb.createWorkbook(file, wb);
//        WritableWorkbook workbook = Workbook.createWorkbook(new File(getPath(fileName)));
        Sheet sheet = workbook.getSheet(sheetName);
        for (int i = 0; i < sheet.getRows(); i++)
            for (int j = 0; j < sheet.getColumns(); j++) {
                boolean isPassId = sheet.getCell(0, i).getContents().equals(id);
                boolean isTestResult = sheet.getCell(j, 0).getContents().equals("test_result");
                if (isPassId && isTestResult ) {
//                    && sheet.getCell(j, i).getType() == CellType.LABEL
                    ((WritableSheet) sheet).addCell(new Label(j, i, content));
                }
            }
        workbook.write();
        workbook.close();
        wb.close();
    }

    /**
     * update test_result in excel
     * @param text
     * @param text2
     * @param id
     * @throws Exception
     */
    public static void AssertEqual(String text, String text2, String id) throws Exception {
        if (text.equals(text2)) {
            try {
                ExcelData.updateTestCasePass(id, "通过", "Login", "test_data.xls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ExcelData.updateTestCasePass(id, "未通过", "Login", "test_data.xls");
            throw new Exception("测试未通过");
        }
    }

    /**
     * get one correct data to login
     * @return
     * @throws IOException
     * @throws BiffException
     */
    public static HashMap<String,String> getLoginData() throws IOException, BiffException {
        return (HashMap<String, String>) getExcelData("Login", "test_data.xls")[0][0];
    }

    public static void passById(String id) throws Exception {
        AssertEqual("","",id);
    }

    public static void unPassById(String id) throws Exception {
        AssertEqual("","failed",id);
    }

}
