import { User } from "./User";

export interface Customer {
  id: number;
  password: string;
  user: User;
  created_date: Date;
  address: string;
}
