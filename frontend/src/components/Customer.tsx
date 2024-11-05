import { User } from "./User";

export interface Customer {
  id: number | undefined;
  password: string | undefined;
  user: User;
  created_date: Date | undefined;
  address: string | undefined;
}
