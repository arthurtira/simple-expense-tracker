import { Component, OnInit } from '@angular/core';
import { User } from '../shared/user.model';
import { NgForm } from '@angular/forms';
import { UserService } from '../shared/user.service';
import { ToastrService } from 'ngx-toastr'
import { Router } from '@angular/router'


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  user: User;
  emailPattern = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$";

  constructor(private _userService: UserService, private toastr: ToastrService, private router: Router) { }

  ngOnInit() {
    this.resetForm();
  }

  showSuccess() {
    this.toastr.success('Your account created successfully. ');
  }

  showError() {
    this.toastr.error("Could not create your account. Try again");
  }

  resetForm(form?: NgForm) {
    if (form != null)
      form.reset();
    this.user = {
      username: '',
      password: '',
      email: '',
      firstName: '',
      lastName: ''
    }
  }

  OnSubmit(form: NgForm) {
    console.log("On submit ");
    this._userService.registerUser(form.value)
    .subscribe(res => {
        this.resetForm(form);
        this.showSuccess()
        this.router.navigate(['/login'])
    },
    err => {
      console.log(err)
      this.toastr.error(err);
    });
  }


}
