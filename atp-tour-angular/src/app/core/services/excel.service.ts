import { Injectable } from '@angular/core';
import * as XLSX from 'xlsx';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor() { }

  generateExcelFile(fileName: string, sheetName: string, data: any[]) {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, sheetName);

    var wscols = [
      { wch: 20 },
      { wch: 20 },
      { wch: 20 },
      { wch: 10 },
      { wch: 10 },
      { wch: 5 },
      { wch: 20 },
    ];
    
    ws['!cols'] = wscols;

    XLSX.writeFile(wb, fileName + '-' + new Date().toDateString() + '.xlsx');
  }

}
