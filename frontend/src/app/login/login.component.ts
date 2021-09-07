import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { ToastrService } from 'ngx-toastr';
import { UserService } from "../service/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted: boolean = false;
  invalidLogin: boolean = false;
  constructor(private formBuilder: FormBuilder,
     private router: Router,
      private userService: UserService,
      private toastr:ToastrService) { }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    const loginPayload = {
      username: this.loginForm.controls.username.value,
      password: this.loginForm.controls.password.value
    }
    console.log("this.loginForm.controls.username.value=>" + this.loginForm.controls.username.value);
    console.log("this.loginForm.controls.password.value=>" + this.loginForm.controls.password.value);
    this.userService.login(loginPayload).subscribe(data => {
      console.log(data);
      this.toastr.success("Logged in successfully");
      window.localStorage.setItem('token', data['token']);
      if(data['role']==="ADMIN"){
        this.router.navigate(['list-user']);
      }
      else{
        window.localStorage.setItem('id', data['id']);
        this.router.navigate(['user-details']);
      }
  
    },error=>{
      this.toastr.error("Invalid credentials");
    });
  }
  addUser() {
    this.router.navigate(['add-user']);
  }
  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }



}
