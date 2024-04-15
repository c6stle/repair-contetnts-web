package webml.base.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Arrays;
import java.util.List;

public class WorkbookUtil {

    public XSSFWorkbook workbook;
    public XSSFSheet sheet;

    public Font headFont;
    public Font bodyFont;
    public CellStyle headStyle;
    public CellStyle bodyStyle;
    public DataFormat numberFormat;
    public CellStyle bodyPriceStyle;

    public List<String> headList;
    public List<String> colNmList;

    public int nowRowNum = 0;

    public WorkbookUtil() {
        this.workbook = new XSSFWorkbook();
        this.sheet = this.workbook.createSheet("Sheet1");

        //폰트
        this.headFont = this.workbook.createFont();
        this.headFont.setFontName("맑은 고딕");
        this.bodyFont = this.workbook.createFont();
        this.bodyFont.setFontName("맑은 고딕");

        //스타일
        this.headStyle = this.workbook.createCellStyle();
        this.headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        this.headStyle.setFont(this.headFont);
        this.headStyle.setAlignment(CellStyle.ALIGN_CENTER);

        this.bodyStyle = this.workbook.createCellStyle();
        this.bodyStyle.setFont(this.bodyFont);
        this.bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);

        this.numberFormat = this.workbook.createDataFormat();
        this.bodyPriceStyle = this.workbook.createCellStyle();
        this.bodyPriceStyle.setDataFormat(this.numberFormat.getFormat("#,##0"));
        this.bodyPriceStyle.setFont(this.bodyFont);
        this.bodyPriceStyle.setAlignment(CellStyle.ALIGN_CENTER);
    }

    public WorkbookUtil setHead(String... headNm) {
        this.headList = Arrays.asList(headNm);
        XSSFRow row = this.sheet.createRow(this.nowRowNum);//head row
        for (int i = 0; i < this.headList.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(this.headStyle);
            if (this.headList.get(i) != null) {
                cell.setCellValue(this.headList.get(i));
            } else {
                cell.setCellValue("");
            }
        }
        this.nowRowNum++;
        return this;
    }

    public WorkbookUtil setColNmList(String... colNm) {
        this.colNmList = Arrays.asList(colNm);
        return this;
    }

    public WorkbookUtil setBody(List<CustomMap> list) {
        for (CustomMap map : list) {
            XSSFRow row = this.sheet.createRow(this.nowRowNum);
            for (int j = 0; j < this.colNmList.size(); j++) {
                String key = this.colNmList.get(j);
                XSSFCell cell = row.createCell(j);
                if (map.get(key) instanceof Long) {
                    cell.setCellStyle(this.bodyPriceStyle);
                } else {
                    cell.setCellStyle(this.bodyStyle);
                }
                if (map.get(key) == null) {
                    cell.setCellValue("");
                } else {
                    cell.setCellValue(map.getStr(key));
                }
            }
            this.nowRowNum++;
        }
        return this;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }
}
