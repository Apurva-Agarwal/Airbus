import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user.service";
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { ToastrService } from 'ngx-toastr';
import {Location} from '@angular/common';
@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  user: User;
  editForm: FormGroup;
  constructor(private formBuilder: FormBuilder,
    private router: Router, 
    private userService: UserService,
    private toastr:ToastrService,
    private location:Location) { }

  ngOnInit() {
    let userId = localStorage.getItem("editUserId");
    // if(!userId) {
    //   alert("Invalid action.")
    //   this.router.navigate(['list-user']);
    //   return;
    // }
    this.editForm = this.formBuilder.group({
      id: [],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      country:['',Validators.required],
      password:['']
    });
    this.userService.getUserById(+userId)
      .subscribe( data => {
        this.editForm.setValue(data);
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
    this.userService.updateUser(this.editForm.value)
      .subscribe(
        data => {
          this.router.navigate(['list-user']);
          this.toastr.success("User updated successfully");
        },
        error => {
          alert(error);
        });
  }

}
