import Result from "@/domain/common/Result";
import { handleSuccess } from "@/data/common/service-utils";
import { axiosInstance } from "@/axios";
import User from "@/domain/model/user/User";

export async function register(
  username: string,
  password: string
): Promise<Result<void>> {
  return handleSuccess(
    axiosInstance.post("auth/register", {
      username,
      password
    })
  );
}

export async function authenticate(
  username: string,
  password: string
): Promise<Result<User>> {
  return handleSuccess(
    axiosInstance.post("auth/login", {
      username,
      password
    })
  );
}

export async function logout(): Promise<Result<void>> {
  return handleSuccess(axiosInstance.post("auth/logout"));
}
