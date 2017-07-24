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
    ShippingComponent
  ],
  imports: [
    BrowserModule,
    CustomMaterialModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [
    AuthService,
    AuthGuard,
    UserService,
    IsLoggedInService,
    PaymentService,
    ShippingService,
    OrderService,
    UserDataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
