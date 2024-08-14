import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: '/account-opening'},
  {
    path: 'account-opening',
    loadChildren: () => import('./components/account-opening/account-opening.module').then(m => m.AccountOpeningModule)
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
