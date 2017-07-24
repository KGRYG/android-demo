import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {

  private emailNotExists = false;
  private forgetPasswordEmailSent = false;
  private recoverEmail:string;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  onForgetPassword() {

    this.userService.retrievePassword(this.recoverEmail).subscribe(
      res => {
        this.forgetPasswordEmailSent = true;
      },
      error => {
        let errorMessage = error.text();
        if(errorMessage === "Email not found") this.emailNotExists = true;
      }
    );
  }

}
