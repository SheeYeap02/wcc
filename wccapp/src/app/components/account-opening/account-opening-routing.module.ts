import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AccountOpeningComponent} from './account-opening.component';
import {AccountInfoComponent} from './account-info/account-info.component';
import { UkPostcodeComponent } from './uk-postcode/uk-postcode.component';

const routes: Routes = [
  {
    path: '',
    component: AccountOpeningComponent,
    children: [
      { path: '', redirectTo: 'account-info', pathMatch: 'full' },
      { path: 'account-info', component: AccountInfoComponent, data: { step: 'account-info' } },
      { path: 'uk-postcode', component: UkPostcodeComponent, data: { step: 'uk-postcode' } },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountOpeningRoutingModule {
}
