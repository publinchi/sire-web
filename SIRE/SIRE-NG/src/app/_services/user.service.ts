import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../_models';
import { Config } from '../config';

@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`${Config.apiUrl}/users`);
    }

    getById(id: number) {
        return this.http.get(`${Config.apiUrl}/users/` + id);
    }

    register(user: User) {
        return this.http.post(`${Config.apiUrl}/users/register`, user);
    }

    update(user: User) {
        return this.http.put(`${Config.apiUrl}/users/` + user.id, user);
    }

    delete(id: number) {
        return this.http.delete(`${Config.apiUrl}/users/` + id);
    }
}
