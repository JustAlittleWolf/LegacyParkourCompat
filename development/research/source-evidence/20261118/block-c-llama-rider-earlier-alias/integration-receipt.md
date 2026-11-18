# Llama rider-height earlier-alias integration receipt

## Product integration

- Product branch: `codex/c-llama-rider-earlier-extension`
- Product base: clean accepted Llama product ancestry at `ae40d61bc9a690064dc9d42b0ef8cd5c616c54e3`, including current-main ancestor `aae9c86464638cb5ed7faa535c92569b89484906`.
- Integrated code commit: `2c2aacc9f181be553d62b141a062701f36319e8c`
- Compiled Git tree: `be7d7724249edb27ebb53183b2e738c1cae8b432`
- Existing operation: `PlayerPassengerPlacementHook`, dispatched by `EntityPassengerPlacementMixin`; the candidate-specific mixin and hook were not imported.

The integration reuses `LlamaPassengerPlacement.defaultAdult` and its existing local-client, sole-direct-passenger, vanilla Llama/TraderLlama, adult, scale, and dimension guards. The existing V1_17 provider is the closest applicable change for selected groups V1_15, V1_15_2, V1_16, V1_16_2, and V1_17, so it supplies the source-confirmed `0.67` factor across those groups without redundant providers. Existing V1_17_1 remains, and V1_18/V1_18_2 retain the later `0.6` factor. A V1_14_2 empty provider preserves native passenger placement before the accepted 1.15 boundary. The helper preserves native current X/Z and rotation and falls back to native placement for an ineligible witness or absent provider.

## Bound source evidence

- Accepted exact comparison and alias audits: evidence commit `742eaf6020f1f4659e5e366782227dfc611b53a2`.
- Alias manifest: `README.md`, SHA-256 `767961e6a6e8c3a98a918934d381c04112c23a80aefa39732a38e0554c8a27d9`.
- The manifest binds nine exact Mojmap comparisons and independent verdicts covering 1.15–1.17 aliases, including the 1.15→1.15.1 and 1.15.1→1.15.2 boundaries, 1.15.2→1.16, all enumerated 1.16 aliases through 1.16.5, and 1.16.5→1.17. The accepted operation is the direct ordinary Player passenger Y expression for an adult default Llama or TraderLlama; it does not establish other carriers, custom dimensions, full release-pair coverage, vehicle physics, or runtime parity.
- Change comments cite Mojmap `Llama.positionRider(Entity)` and the relevant Player riding-offset member. The source term is left-associative `llamaY + (getBbHeight() * 0.67) + (-0.35)`; the existing helper retains that operation order while changing only Y.
- Intended central association: `development/research/findings/1.17.1--1.18.2/llama-passenger-rider-height.md`, with this receipt binding the accepted earlier-alias extension.

## Static review and build disposition

- Independent product static review: `c_extended_llama_candidate_independent_review` signed PASS for exact code commit `2c2aacc9f181be553d62b141a062701f36319e8c` and tree `be7d7724249edb27ebb53183b2e738c1cae8b432`; reviewer verified exact tree and ancestry.
- Exact command: `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`
- Result: exit code `0`, `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up-to-date. `compileClientJava` ran. Every Gradle `Test` task was disabled by the init script and `-x test` was supplied.
- The build compiled exact code commit `2c2aacc9f181be553d62b141a062701f36319e8c` and Git tree `be7d7724249edb27ebb53183b2e738c1cae8b432`.
- Compilation is not evidence of movement parity. No tests or runtime launches were performed.
