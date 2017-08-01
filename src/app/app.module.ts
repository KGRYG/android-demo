import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import 'hammerjs';

import { AppComponent } from './app.component';
import {CustomMaterialModule} from './modules/custommaterial.module';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import { HomeComponent } from './components/home/home.component';
import {AppRoutingModule} from './modules/app-routing.module';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import {AuthService} from './services/auth.service';
import { MyAccountComponent } from './components/my-account/my-account.component';
import {AuthGuard} from './services/auth-guard.service';
import {UserService} from './services/user.service';
import {IsLoggedInService} from './services/is-logged-in.service';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { LoginComponent } from './components/my-account/login/login.component';
import { NewAccountComponent } from './components/my-account/new-account/new-account.component';
import { ForgetPasswordComponent } from './components/my-account/forget-password/forget-password.component';
import {PaymentService} from "./services/payment.service";
import {ShippingService} from "./services/shipping.service";
import {OrderService} from "./services/order.service";
import { EditComponent } from './components/my-profile/edit/edit.component';
import { OrdersComponent } from './components/my-profile/orders/orders.component';
import { PaymentComponent } from './components/my-profile/payment/payment.component';
import { ShippingComponent } from './components/my-profile/shipping/shipping.component';
import {UserDataService} from "./services/user-data.service";
import { BookListComponent } from './components/book-list/book-list.component';
import {BookService} from "./services/book.service";
import {DataFilterPipe} from "./components/book-list/data-filter.pipe";
import {DataTableModule} from 'angular2-datatable';
import { BookDetailComponent } from './components/book-detail/book-detail.component';
import {BookDataService} from "./services/book-data.service";
import {CartService} from "./services/cart.service";
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { OrderComponent } from './components/order/order.component';
import { ReviewComponent } from './components/order/review/review.component';
import { OrderShippingComponent } from './components/order/order-shipping/order-shipping.component';
import { OrderPaymentComponent } from './components/order/order-payment/order-payment.component';
import {OrderDataService} from "./services/order-data.service";
import {CheckoutService} from "./services/checkout.service";
import {OrderSummaryComponent} from "./components/order-summary/order-summary.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavBarComponent,
    MyAccountComponent,
    MyProfileComponent,
    LoginComponent,
    NewAccountComponent,
    ForgetPasswordComponent,
    EditComponent,
    OrdersComponent,
    PaymentComponent,
    ShippingComponent,
    BookListComponent,
    DataFilterPipe,
    BookDetailComponent,
    ShoppingCartComponent,
    OrderComponent,
    ReviewComponent,
    OrderShippingComponent,
    OrderPaymentComponent,
    OrderSummaryComponent
  ],
  imports: [
    BrowserModule,
    CustomMaterialModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    DataTableModule
  ],
  providers: [
    AuthService,
    AuthGuard,
    UserService,
    IsLoggedInService,
    PaymentService,
    ShippingService,
    OrderService,
    UserDataService,
    BookService,
    BookDataService,
    CartService,
    OrderDataService,
    CheckoutService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
