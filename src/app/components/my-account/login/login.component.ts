import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loginError = false;
  private credential = {'username':'', 'password':''};

  constructor(private router: Router,
              private authService: AuthService
              ) { }

  ngOnInit() {
  }

  onLogin() {
    this.authService.sendCredentials(this.credential.username, this.credential.password).subscribe(
      res => {
        localStorage.setItem("xAuthToken", res.json().token);
        this.router.navigate(['/home']);
      },
      error => {
        this.loginError = true;
      }
    );
  }

}
