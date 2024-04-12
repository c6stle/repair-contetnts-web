package webml.base.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
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
    public List<String> headList;
    public List<String> colNmList;

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
    }

    public WorkbookUtil setHead(String... headNm) {
        this.headList = Arrays.asList(headNm);
        XSSFRow row = this.sheet.createRow(0);//head row
        for (int i = 0; i < this.headList.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(this.headStyle);
            if (this.headList.get(i) != null) {
                cell.setCellValue(this.headList.get(i));
            } else {
                cell.setCellValue("");
            }
        }
        return this;
    }

    public WorkbookUtil setColNmList(String... colNm) {
        this.colNmList = Arrays.asList(colNm);
        return this;
    }

    public WorkbookUtil setBody(List<?> list) {
        //for ()

        return this;
    }


}
