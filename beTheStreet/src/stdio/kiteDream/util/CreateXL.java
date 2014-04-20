package stdio.kiteDream.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import stdio.kiteDream.module.user.bean.User;

public class CreateXL {

	/** Excel 文件要存放的位置 */

	public static File createUserExcel(List<User> users) {
		String outputFile = new Date().getTime()+"";
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet1");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Nickname");
			cell = row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("register time");
			cell = row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("green coin");
			cell = row.createCell(3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("yellow coin");
			cell = row.createCell(4);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("red coin");
			cell = row.createCell(5);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("level");
			cell = row.createCell(6);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("score");
			cell = row.createCell(7);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("logins");
			cell = row.createCell(8);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("total time");
			cell = row.createCell(9);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("group type");
			cell = row.createCell(10);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("group org");
			cell = row.createCell(11);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("group name");
			cell = row.createCell(12);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("group creater");
			int rowIndex= 1;
			for(User user: users){
				try {
					row = sheet.createRow(rowIndex++);
					cell = row.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getNickname());
					cell = row.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getCreate_time()==null?"":Constant.TIME.format(user.getCreate_time()));
					cell = row.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getCoins()==null?0:user.getCoins().getGreenNum());
					cell = row.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getCoins()==null?0:user.getCoins().getYellowNum());
					cell = row.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getCoins()==null?0:user.getCoins().getRedNum());
					cell = row.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getHigh_level());
					cell = row.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getHigh_level_stage()+"/"+user.getHigh_level_all());
					cell = row.createCell(7);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("logins");
					cell = row.createCell(8);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("total time");
					cell = row.createCell(9);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getGroup()==null?"":user.getGroup().getCategory().getName());
					cell = row.createCell(10);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getGroup()==null?"":user.getGroup().getGroupOrg().getName());
					cell = row.createCell(11);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getGroup()==null?"":user.getGroup().getName());
					cell = row.createCell(12);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getGroup()==null?false:user.getGroup().getCreatername().equals(user.getNickname()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			FileOutputStream fOut = new FileOutputStream(outputFile);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new File(outputFile);
	}
}