import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MdButtonModule,
  MdCardModule,
  MdCheckboxModule,
  MdDatepickerModule,
  MdDialogModule,
  MdIconModule,
  MdInputModule,
  MdMenuModule,
  MdOptionModule,
  MdSelectModule,
  MdTabsModule,
  MdToolbarModule,
  MdGridListModule,
  MdSlideToggleModule
} from '@angular/material';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    MdToolbarModule,
    MdIconModule,
    MdMenuModule,
    MdButtonModule,
    MdInputModule,
    MdSelectModule,
    MdOptionModule,
    MdCardModule,
    MdCheckboxModule,
    MdTabsModule,
    MdDatepickerModule,
    BrowserAnimationsModule,
    MdDialogModule,
    MdGridListModule,
    MdSlideToggleModule
  ]
})
export class CustomMaterialModule { }
