import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import {HomeComponent} from '../components/home/home.component';
import {MyAccountComponent} from '../components/my-account/my-account.component';
import {AuthGuard} from '../services/auth-guard.service';
import {IsLoggedInService} from '../services/is-logged-in.service';
import {LoginComponent} from "../components/my-account/login/login.component";
import {NewAccountComponent} from "../components/my-account/new-account/new-account.component";
import {ForgetPasswordComponent} from "../components/my-account/forget-password/forget-password.component";
import {MyProfileComponent} from "../components/my-profile/my-profile.component";
import {EditComponent} from "../components/my-profile/edit/edit.component";
import {OrdersComponent} from "../components/my-profile/orders/orders.component";
import {PaymentComponent} from "../components/my-profile/payment/payment.component";
import {ShippingComponent} from "../components/my-profile/shipping/shipping.component";
import {BookListComponent} from "../components/book-list/book-list.component";
import {BookDetailComponent} from "../components/book-detail/book-detail.component";
import {ShoppingCartComponent} from "../components/shopping-cart/shopping-cart.component";
import {OrderComponent} from "../components/order/order.component";
import {OrderShippingComponent} from "../components/order/order-shipping/order-shipping.component";
import {OrderPaymentComponent} from "../components/order/order-payment/order-payment.component";
import {ReviewComponent} from "../components/order/review/review.component";
import {OrderSummaryComponent} from "../components/order-summary/order-summary.component";

const appRoutes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'booklist', component: BookListComponent},
  {path: 'bookdetail/:id',component: BookDetailComponent},
  {path: 'shoppingcart', component: ShoppingCartComponent},
  {path: 'ordersummary', component: OrderSummaryComponent, canActivate: [AuthGuard]},
  {path: 'myaccount', redirectTo: '/myaccount/login', pathMatch: 'full'},
  {path: 'myaccount', component: MyAccountComponent, resolve: [IsLoggedInService], children: [
    {path: 'login', component: LoginComponent},
    {path: 'new-account', component: NewAccountComponent},
    {path: 'forget-password', component: ForgetPasswordComponent}
  ]},
  {path: 'myprofile', redirectTo: '/myprofile/edit', pathMatch: 'full'},
  {path: 'myprofile', component: MyProfileComponent, canActivate: [AuthGuard], children: [
    {path: 'edit', component: EditComponent},
    {path: 'orders', component: OrdersComponent},
    {path: 'payment', component: PaymentComponent},
    {path: 'shipping', component: ShippingComponent}
  ]},
  {path: 'order', redirectTo: '/order/shipping', pathMatch: 'full'},
  {path: 'order', component: OrderComponent, canActivate: [AuthGuard], children: [
    {path: 'shipping', component: OrderShippingComponent},
    {path: 'payment', component: OrderPaymentComponent},
    {path: 'review', component: ReviewComponent}
  ]}
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes, {preloadingStrategy: PreloadAllModules})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
