import { Store } from "vuex";

export function pxToNumber(px: string): number {
  return parseInt(px.substring(0, px.length - 2));
}

export function prependZeros(s: string, length: number): string {
  return ("0".repeat(length) + s).slice(-Math.max(s.length, length));
}

export function timeFormatted(date: Date): string {
  const hours = prependZeros(date.getHours().toString(), 2);
  const minutes = prependZeros(date.getMinutes().toString(), 2);
  return `${hours}:${minutes}`;
}

export function commitStoreChange<T>(
  store: Store<any>,
  action: string,
  mutator: any
) {
  store.commit(action, (state: T) => {
    return { ...state, ...mutator };
  });
}
