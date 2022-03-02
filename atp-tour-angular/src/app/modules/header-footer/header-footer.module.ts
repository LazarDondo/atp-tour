import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FooterComponent } from 'src/app/features/footer/footer.component';
import { HeaderComponent } from 'src/app/features/header/header.component';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent
  ],
  imports:[
    BrowserModule
  ],
  exports:[
    HeaderComponent,
    FooterComponent
  ]
})
export class HeaderFooterModule { }
