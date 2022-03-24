import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FooterComponent } from 'src/app/features/footer/footer.component';
import { HeaderComponent } from 'src/app/features/header/header.component';
import { TranslateModule } from '@ngx-translate/core';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent
  ],
  imports:[
    BrowserModule,
    TranslateModule
  ],
  exports:[
    HeaderComponent,
    FooterComponent
  ]
})
export class HeaderFooterModule { }
