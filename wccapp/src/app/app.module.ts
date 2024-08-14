import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {IconsProviderModule} from './icons-provider.module';
import {NzLayoutModule} from 'ng-zorro-antd/layout';
import {NzMenuModule} from 'ng-zorro-antd/menu';
import {NzBreadCrumbComponent, NzBreadCrumbItemComponent} from 'ng-zorro-antd/breadcrumb';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {AccountOpeningComponent} from './components/account-opening/account-opening.component';
import {CommonModule} from '@angular/common';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {HttpClientModule} from '@angular/common/http';
// import { UserOutline, LaptopOutline, AlertOutline } from '@ant-design/icons-angular/icons';
import * as AllIcons from '@ant-design/icons-angular/icons';
import {IconDefinition} from '@ant-design/icons-angular';
import {NzGridModule} from 'ng-zorro-antd/grid';
import {NzListComponent, NzListHeaderComponent, NzListFooterComponent, NzListItemComponent} from 'ng-zorro-antd/list';
import {NzFlexModule} from 'ng-zorro-antd/flex';
import {NzStepsModule} from 'ng-zorro-antd/steps';
import {NzFormModule, NzFormItemComponent, NzFormLabelComponent} from 'ng-zorro-antd/form';
/** config angular i18n **/
import {registerLocaleData} from '@angular/common';
import en from '@angular/common/locales/en';

registerLocaleData(en);

/** config ng-zorro-antd i18n **/
import {provideNzI18n, en_US} from 'ng-zorro-antd/i18n';

const antDesignIcons = AllIcons as {
  [key: string]: IconDefinition;
};
const icons: IconDefinition[] = Object.keys(antDesignIcons).map(key => antDesignIcons[key])

@NgModule({
  declarations: [
    AppComponent,
    AccountOpeningComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    NoopAnimationsModule,
    AppRoutingModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    NzBreadCrumbComponent,
    NzBreadCrumbItemComponent,
    NzIconModule.forChild(icons),
    // NzIconModule.forRoot([UserOutline, LaptopOutline]),
    HttpClientModule,
    NzGridModule,
    NzListComponent,
    NzListHeaderComponent,
    NzListFooterComponent,
    NzListItemComponent,
    NzFlexModule,
    NzStepsModule,
    NzFormModule,
    NzFormItemComponent,
    NzFormLabelComponent
  ],
  providers: [provideNzI18n(en_US)],
  bootstrap: [AppComponent]
})
export class AppModule {
}
