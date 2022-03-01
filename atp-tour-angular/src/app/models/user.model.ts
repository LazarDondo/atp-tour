import { Injectable } from "@angular/core";
import { Role } from "./role.model";

export class User {
    id?: number;
    username: string;
    password: string;
    firstName?: string;
    lastName?: string;
    enabled: boolean;
    roles: Array<Role>;
}
