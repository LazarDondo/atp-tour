import { Role } from "./role.model";

/**
 * Class representing an app user
 */
export class User {
    id?: number;
    username: string;
    password: string;
    firstName?: string;
    lastName?: string;
    enabled: boolean;
    roles: Array<Role>;
}
