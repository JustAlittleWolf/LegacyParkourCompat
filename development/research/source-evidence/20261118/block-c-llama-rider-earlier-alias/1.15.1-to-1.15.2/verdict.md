# Independent audit: Llama rider Y placement, 1.15.1 → 1.15.2

## Verdict

**Accepted for the bounded slice; no difference found.** The frozen report's claims about the ordinary adult `Llama` or inherited `TraderLlama` placing one direct ordinary `Player` passenger are supported by the exact adjacent Mojmap sources. This verdict does not establish full-pair coverage.

## Frozen input identity

- Input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-1151-1152-position-intermediate/report.md`
- SHA-256 of the exact report bytes, computed with PowerShell `Get-FileHash -Algorithm SHA256`: `b1772fcc257de9be8bfbd8a00b7c5ce7cc3dd901dd241d021ffd08592760187e`.
- Compared only the report and permitted exact source/provenance files. No product implementation, Git history, wiki material, network source, build, test or launch was consulted.

## Exact source identity and integrity

Canonical adjacent roots inspected: `decompiled_minecraft/1.15.1/mojmap/` and `decompiled_minecraft/1.15.2/mojmap/`.

- `1.15.1/mojmap.ready.json` identifies version `1.15.1`, mapping `mojmap`; `1.15.1/mojmap.provenance.json` also identifies `1.15.1` / `mojmap` and run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`. The ready marker's provenance SHA-256 matches the actual provenance file. The source manifest's actual SHA-256 is `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`, matching the ready marker.
- `1.15.2/mojmap.ready.json` identifies version `1.15.2`, mapping `mojmap`; `1.15.2/mojmap.provenance.json` identifies `1.15.2` / `mojmap`. The source manifest's actual SHA-256 is `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, matching the ready marker. This provenance file is the available version/mapping provenance record; it does not contain a source-manifest digest, so the ready marker supplies that link.
- Every listed Java hash below matches both the actual file and that version's `mojmap.sources.sha256` entry. The legacy-named `.decompile-provenance.json` is absent; the per-mapping `mojmap.provenance.json` and ready marker are present at each version root.

| Relative source file | 1.15.1 SHA-256 | 1.15.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/animal/horse/Llama.java` | `bbe98226c5afaddce8ca7458c721fbefff592cc75e02bc261d6b440ca6877f19` | same |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` | same |
| `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `a967d0834d3c28080725a8b639d2c8c182ee6a7209a361d462fb0225a2836e6d` | same |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | same |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |

## Independent comparison

- `Llama.java:149-160` in both versions guards placement with `this.hasPassenger(entity)` and calls `entity.setPos(...)`. Its Y argument is exactly `this.getY() + this.getRideHeight() + entity.getRidingHeight()`. Java's left-associative additions operate on `double` values: `getY`, `getRideHeight`, and `getRidingHeight` return `double`. `Llama.getRideHeight()` at line 159 returns `this.getBbHeight() * 0.67`; the unsuffixed `0.67` is a `double` literal, so the product and returned value are double. There is no float intermediate or cast in the Y expression.
- `Player.java:986-988` in both versions overrides `getRidingHeight()` and returns `-0.35` as a double. Therefore the Y computation has the same terms and order in both versions: mount base Y, plus 0.67 times llama bounding-box height, plus rider height -0.35.
- The same `Llama.positionRider` retains the same horizontal float calculations and offsets (`0.3F * g`, `-0.3F * f`) in both releases. They do not alter the Y result.
- `Player.rideTick()` (`Player.java:459` onward) checks `!this.level.isClientSide` before its stop-riding branch. A client-side player therefore takes the `else` branch and calls `super.rideTick()`. `Entity.rideTick()` (`Entity.java:1544-1549`) calls `this.getVehicle().positionRider(this)` when the player is a passenger. This is virtual dispatch to `Llama.positionRider` for an ordinary Llama; `TraderLlama.java:22` declares `TraderLlama extends Llama` and has no `positionRider` override.
- `Llama.positionRider` overrides rather than calls `super.positionRider`. The inherited `AbstractHorse.positionRider` (`AbstractHorse.java:900-905`) calls its superclass placement and then adjusts `yBodyRot` for a `Mob`; that inherited body is bypassed by the Llama override. The override and superclass body are byte-identical across the compared releases.
- The cited `Player.rideTick()` bodies are text-identical; normalized extracted-body SHA-256 is `78bc94d65c4f6cac619894a3e4cf89f89f7378ad2a6aa76c4f96b8a6699cc80d` on both sides. The `getRidingHeight()` bodies are text-identical; normalized extracted-body SHA-256 is `eeef08b943915eab809838059c4872ad884229ef4266061d9210338dba60ac63` on both sides.

## Scope boundary

The complete `Player.java` files differ by SHA-256, while the two cited Player method bodies are identical. This audit did not inspect or characterize other Player.java differences. Other mounts, rider classes, passenger stacks, and non-player/vehicle movement remain outside this finding.
