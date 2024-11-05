import { Customer } from "./Customer";
import { Status } from "./Status";

export interface Account {
  id: number | undefined;
  balance: number | undefined;
  customer: Customer | undefined;
  name: string | undefined;
  start: Date | undefined;
  status: Status | undefined;
}
