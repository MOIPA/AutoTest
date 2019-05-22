package com.main.data;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

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
    public Object[][] getExcelData(String sheetName, String fileName) throws IOException, BiffException {
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
            data[i-1][0] = new HashMap<>();
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
    private String getPath(String fileName) throws IOException {
        File file = new File(".");
        return file.getCanonicalPath() + "\\src\\main\\resources\\" + fileName;
    }
}
