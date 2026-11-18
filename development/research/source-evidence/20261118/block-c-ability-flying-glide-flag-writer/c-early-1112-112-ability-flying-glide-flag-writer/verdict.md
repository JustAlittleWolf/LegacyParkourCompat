# Independent source-audit verdict

**Verdict: ACCEPT — bounded claim supported.**

## Immutable inputs

- Frozen claim: `c-early-1112-112-ability-flying-glide-flag-writer/report.md`, SHA-256 `17302b3ba7b606615d1fc9da194fff35085b9a352d7e632d7cb716eef6c33138` (verified before review).
- Pair: exact Minecraft `1.11.2` → initial stable release `1.12`; both use Ornithe Feather sources.
- `1.11.2` source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; ready marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; sidecar provenance SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; artifact manifest SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`.
- `1.12` canonical `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; it contains 2,048 source entries. Its pinned client hash is `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; Feather 1.12 build.2 merged archive hash is `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`, with mapping metadata hash `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`. The pinned release metadata is the `/1.12.json` object, SHA-256 `33cc40a05466b4e34e04e65def732094388e9149eb6bd37e5daa227c06cbde4b`.
- The `1.11.2` ready marker pins the `1.11.2` client (`be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`) and the Feather build.2 mapping artifacts; the merged archive hash is `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757` and Tiny mapping hash is `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`. Its verification record reports 1,921 source files checked, zero source differences/missing files and no raw input artifact differences. The recorded derived remapped-intermediate difference does not alter the published source tree.

All cited Java bytes match the exact manifests:

| Source | 1.11.2 SHA-256 | 1.12 SHA-256 |
|---|---|---|
| `LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| `PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` |
| `LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |

## Trace and finding

In both releases, `LivingEntity.tick` runs `flyingTick` and then invokes the virtual `moveRelative`. The local player inherits the `PlayerEntity` override and reports locally controlled. `flyingTick` keeps flag 7 live only while it is airborne, not riding, and has a fly-capable Elytra; its write is server-only, so this local client path retains the live flag entering movement.

The ability-flight branch in `PlayerEntity.moveRelative` is gated by `abilities.flying && !isRiding()`. It calls the superclass movement first, then restores/damps vertical velocity and ends with `setFlag(7, false)`. The superclass reaches `LivingEntity.moveRelative`; when flag 7 is live, its glide branch runs before ordinary input handling, updates glide velocity, invokes `this.move(MoverType.SELF, ...)`, and returns through the local-player `move` override before the player tail clear. That local override delegates to the inherited `Entity.move`; the intervening `ClientPlayerEntity`/`PlayerEntity` hierarchy has no competing `move` override in these sources. The glide branch’s landing clear is gated on `onGround && !world.isClient`, so it does not replace the later local-player ability-flight clear.

The same ordering and gates hold on both sides. The 1.12 caller and method add `verticalSpeed` as the third argument. The live-glide branch does not read that argument; its vertical velocity update is driven by the existing glide equations. Thus the bounded report correctly treats the extra argument as an API boundary, not as a change to this active-glide path. The ability-flight negative branch only calls superclass movement and does not execute the direct player tail clear.

Relevant source locations: 1.11.2 `PlayerEntity.moveRelative` 1387–1405, `LivingEntity.moveRelative` 1388–1439, `LivingEntity.tick` caller / `flyingTick` 1798–1829, `LocalClientPlayerEntity.move` 823–829; 1.12 corresponding locations 1386–1404, 1425–1477, 1842–1873, and 840–846.

This verdict accepts only the specified ordinary local-player ability-flying / already-live Elytra flag trace and the changed vertical-argument boundary. It is not a full-pair coverage audit or a runtime-parity claim.
