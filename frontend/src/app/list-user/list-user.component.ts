import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../service/user.service";
import {User} from "../model/user.model";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {
  p: number = 1;

  users: User[];
  //sorting
  key: string = 'firstName'; //set default
  reverse: boolean = false;
 
  constructor(private router: Router,
    private userService: UserService,
    private toastr:ToastrService) { }

  ngOnInit() {
    this.getUsers();

  }
  getUsers(){
    this.userService.getUsers()
      .subscribe( data => {
        this.users = data;
      });
  }

  deleteUser(id:any): void {
 
    this.userService.deleteUser(id)
      .subscribe( data => {
       this.getUsers();
        this.toastr.success("User Deleted successfully");
      })
  };

  updateUser(id:number): void {
    localStorage.removeItem("editUserId");
    localStorage.setItem("editUserId", id.toString());
    this.router.navigate(['edit-user']);
  };

  addUser(): void {
    this.router.navigate(['add-user']);
  };
  sort(key){
    this.key = key;
    this.reverse = !this.reverse;
  }
  logout(){
    localStorage.removeItem("token");
    this.router.navigate(['login']);
  }
 
}
