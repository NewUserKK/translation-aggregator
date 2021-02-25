import { handleSuccess } from "@/data/common/service-utils";
import { axiosInstance } from "@/axios";
import Result from "@/domain/common/Result";

export async function getHistory(): Promise<Result<string[]>> {
  return handleSuccess(axiosInstance.get("history"));
}
