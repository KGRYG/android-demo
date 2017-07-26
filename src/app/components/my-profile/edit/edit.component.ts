import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../../services/user.service";
import {User} from "../../../models/user";
import {Subscription} from "rxjs/Subscription";
import {UserDataService} from "../../../services/user-data.service";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, OnDestroy {

  updateSuccess = false;
  newPassword: string;
  incorrectPassword = false;
  currentPassword: string;
  user: User = new User();
  subscription: Subscription;

  constructor(private userService: UserService,
              private userData: UserDataService) { }

  ngOnInit() {
    this.subscription = this.userData.userChanged
      .subscribe(
        (user) => {
          this.user = user;
        }
      );
    this.user = this.userData.getUser();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onUpdateUserInfo () {
    this.userService.updateUserInfo(this.user, this.newPassword, this.currentPassword).subscribe(
      res => {
        this.userData.setUser(this.user);
        this.updateSuccess = true;
      },
      error => {
        let errorMessage = error.text();
        if(errorMessage === "Incorrect current password!") this.incorrectPassword = true;
      }
    );
  }

}
