import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from "../model/user.model";

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }
  baseUrl: string = 'http://localhost:8080/api/users/';

  getUsers() {
    return this.http.get<User[]>(this.baseUrl + 'list');
  }

  getUserById(id: number) {
    return this.http.get<User>(this.baseUrl + 'getUserById/' + id);
  }
  login(loginPayload) {
    return this.http.post(this.baseUrl + 'login', loginPayload);
  }
  createUser(user: User) {
    return this.http.post(this.baseUrl+'signup', user);
  }

  updateUser(user: User) {
    return this.http.post(this.baseUrl + 'updateUser', user);
  }

  deleteUser(userId: number) {
    return this.http.delete(this.baseUrl + 'delete/' + userId);
  }
}
