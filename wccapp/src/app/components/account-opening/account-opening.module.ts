import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';


import {AccountOpeningRoutingModule} from './account-opening-routing.module';
import {AccountInfoComponent} from './account-info/account-info.component';
import {NzFormModule, NzFormItemComponent, NzFormLabelComponent, NzFormControlComponent} from 'ng-zorro-antd/form';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzDatePickerModule} from 'ng-zorro-antd/date-picker';
import {NzToolTipModule} from 'ng-zorro-antd/tooltip';
import { NzMessageModule } from 'ng-zorro-antd/message';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzUploadModule } from 'ng-zorro-antd/upload';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTableModule, NzTableSortFn, NzTableSortOrder } from 'ng-zorro-antd/table';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';

import * as AllIcons from '@ant-design/icons-angular/icons';
import {IconDefinition} from '@ant-design/icons-angular';
import {NzIconModule} from 'ng-zorro-antd/icon';
import { UkPostcodeComponent } from './uk-postcode/uk-postcode.component';

const antDesignIcons = AllIcons as {
  [key: string]: IconDefinition;
};
const icons: IconDefinition[] = Object.keys(antDesignIcons).map(key => antDesignIcons[key])

@NgModule({
  declarations: [
    AccountInfoComponent,
    UkPostcodeComponent,
  ],
  imports: [
    CommonModule,
    AccountOpeningRoutingModule,
    NzFormModule,
    NzFormItemComponent,
    NzFormLabelComponent,
    NzFormControlComponent,
    FormsModule,
    ReactiveFormsModule,
    NzSelectModule,
    NzInputModule,
    NzButtonComponent,
    NzDatePickerModule,
    NzIconModule.forChild(icons),
    NzInputModule,
    NzToolTipModule,
    NzMessageModule,
    NzRadioModule,
    NzCheckboxModule,
    NzUploadModule,
    NzModalModule,
    NzCardModule,
    NzTableModule,
    NzSpinModule,
    NzDrawerModule
  ]
})
export class AccountOpeningModule {
}
