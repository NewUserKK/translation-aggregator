export default class UrbanTranslationResult {
  constructor(
    public readonly word: string = "",
    public readonly definition: string = "",
    public readonly example: string = "",
    public readonly defId: number = 0
  ) {}
}
