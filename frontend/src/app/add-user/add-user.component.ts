import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../service/user.service";
import { ToastrService } from 'ngx-toastr';
import {Router} from "@angular/router";
import {Location} from '@angular/common';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
    private router: Router, private userService: UserService,
    private toastr:ToastrService,
    private location:Location) { }
  loggedInUser:boolean = false;
  addForm: FormGroup;

  ngOnInit() {
    if(localStorage.getItem('token')){
      console.log("inside")
      this.loggedInUser = true;
    }
    this.addForm = this.formBuilder.group({
      id: [],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['',Validators.required],
      country : ['',Validators.required]
    });

  }
  goBack(){
    this.location.back();
  }
  logout(){
    localStorage.removeItem("token");
    this.router.navigate(['login']);
  }
 

  onSubmit() {
    this.userService.createUser(this.addForm.value)
      .subscribe( data => {
        this.location.back();
        this.toastr.success("User Added successfully");
      });
  }

}
