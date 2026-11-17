# Independent source audit — ordinary ground jump r2, 26.1.2 → 26.2

Report: `build/later-source-20261116/26.1.2--26.2/ground_jump/ordinary-ground-jump-r2.md`  
Report SHA-256: `566419FB17B6530A87FD556FE85A2EF1DF7EAD5B3FFF2ED8686D99DEB8D521BC`

## Verdict

**ACCEPT** for the bounded ordinary dry-ground, non-flying local-player ground-jump source path. The added r2 input bindings close the two dependencies identified by the r1 audit. This accepts only the stated source path and its no-difference result; it does not freeze the pair or establish runtime parity.

## r2 binding checks

The r2 is an exact-byte prefix of the original report plus an additive input-binding section: the original r1 file is 7,821 bytes and all bytes match the beginning of r2 (10,245 bytes). The report's current digest matches the bound SHA above.

At both endpoints, `ClientInput.java` has SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` in the exact source manifest and on disk. `makeJump()` constructs a new `Input`, preserves the movement/shift/sprint components, and passes `true` in the fifth `jump` component position. `Input.java` has SHA-256 `ee9ce4cfd647e09c55a9562f917fa504c0fba081bcfbbeb15871cf70818bc447` in each endpoint manifest and on disk. Its record component order makes the fifth value `jump`; its codec encodes jump as flag 16 and decodes that flag into the jump component. The file bytes are identical across endpoints.

The r2 report also binds these dependencies to unchanged exact `LocalPlayer` call sites: `aiStep()` calls `input.makeJump()` in the auto-jump branch, and `applyInput()` reads `input.keyPresses.jump()`. Both calls are present in the exact endpoint sources. The six r1 movement/source identity bindings and unchanged dry-ground call-path claims were already checked against both endpoint manifests and disk by the prior audit; this audit reuses those unchanged checks rather than reopening them. The r1 audit's only required correction was these two missing input bindings.

## Scope limits

The accepted no-difference claim remains limited to the ordinary dry, grounded, non-flying local-player jump path. The report excludes the mayfly creative-flight toggle, fluid/lava jump branch, fall-flying, vehicles, server-authoritative packet handling and non-player jump behavior. Block jump-factor providers/support-block state dependencies and effective runtime attribute/effect modifiers remain open. No resource membership, generated-world state, runtime parity, or whole-pair coverage is established.
