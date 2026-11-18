# Llama rider height integration receipt

## Product integration

- Product branch: `codex/c-llama-rider-extension`
- Product base: current `main` ancestry `aae9c86464638cb5ed7faa535c92569b89484906`
- Integrated code commit: `4e324652522b935ce5c4d59336be3f413c79385c`
- Compiled Git tree: `d98ac539716fb568a0eaccceb8ec9353f92f5992`
- Existing shared operation: `PlayerPassengerPlacementHook`, dispatched by `EntityPassengerPlacementMixin`; no new hook or mixin.

The integration changes only local-client direct passenger Y placement for an adult, default-scale, default-dimension vanilla Llama or TraderLlama that is the sole direct passenger. It preserves the native current X/Z position and rotation, checks the passenger relationship and client level, and falls back to the native callback when the witness does not match. The historical factor is `0.67` for the selected 1.17 profiles and `0.6` for 1.18 through 1.20.1. The empty V1_16_2 provider preserves native placement for earlier profiles. V1_19_4 supplies `0.6` only for eligible Llama and TraderLlama witnesses and returns to native placement for other carriers; the 1.20.2 attachment-point design is outside this old-offset implementation. Existing other-carrier behavior remains in the V1_20 provider.

## Bound source evidence

- Accepted 1.17.1–1.18 comparison: `comparison.md`, SHA-256 `2f9c33ea502b8d27f7c497d4cb01827a524caa04860f7942714fba877f6bfc2e`.
- Independent audit verdict for that comparison: `verdict.md`, SHA-256 `3d91a3be598c66bccc724b5182dce96651f68cc40b0cb305f6b74846be49d9e3`.
- 1.18.2–1.20.1 comparison: `1.18.2-1.20.1/report.md`, SHA-256 `7c01b54e8bc570d2b80fc91a5e90fa2d0a7d623c24cafb7047e2cc3975cd638b`.
- Independent 1.18.2–1.20.1 audit verdict: `1.18.2-1.20.1/verdict.md`, SHA-256 `85fccaba6f2313e97d37561c2c568ba2d6851d9c95b641f3cbd2369574d50952`.

The Change comments cite paired Mojmap source members. Source evidence covers the named 1.17.1/1.18 difference, the checked unchanged 1.18.2–1.20.1 interval, and the changed attachment-point operation at 1.20.2. It does not establish full release-pair coverage or runtime parity. The 1.20 provenance caveat in the accepted report remains applicable.

## Build disposition

- Exact command: `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`
- Result: `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up-to-date. Every Gradle `Test` task was disabled by the init script and `-x test` was supplied.
- Build ran after the source-comment correction on the exact committed Java tree `d98ac539716fb568a0eaccceb8ec9353f92f5992`.
- Representative compiled class SHA-256 values:
  - `v1_17/LlamaRiderHeight.class`: `7D0EAFDF45F89F6FA3808DDFC8FEBB291C5DD007939189059B7C9E61AA50E02F`.
  - `v1_19_4/NativePlayerPassengerPlacement.class`: `D34A4332CA37B0B76E7A5E08EF7D0541DE7D10BAE584224903A0C034E45B84B9`.
  - `llama/LlamaPassengerPlacement.class`: `F7A357B57E89AB5C74E7C2F4E7D984139BBB6FA8EEF38AD776C4EA713B42334E`.

Intended central finding association: `development/research/findings/1.17.1--1.18.2/llama-passenger-rider-height.md`. Compilation is not a movement-parity claim.
