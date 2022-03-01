import { NgModule } from '@angular/core';
import { FooterComponent } from 'src/app/features/footer/footer.component';
import { HeaderComponent } from 'src/app/features/header/header.component';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent
  ],
  exports:[
    HeaderComponent,
    FooterComponent
  ]
})
export class HeaderFooterModule { }
