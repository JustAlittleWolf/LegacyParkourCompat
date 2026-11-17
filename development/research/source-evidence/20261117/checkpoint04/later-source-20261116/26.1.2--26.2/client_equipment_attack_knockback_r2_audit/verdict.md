# Independent source audit: client equipment ATTACK_KNOCKBACK r2

## Verdict

**ACCEPT**, bound to report SHA-256 8ABA863E1776ACBFD5D4E2B9DC6B2FD190F0E97C6015768EF1E2526D7A5B5F58 at build/later-source-20261116/26.1.2--26.2/client_equipment_attack_knockback_revision_r2/coverage-r2.md.

The r2 revision corrects the sole material r1 issue: the equipment-slot ItemStack.forEachModifier overload is also used by LivingEntity.stopLocationBasedEffects to remove modifiers from a prior stack. That path is distinguished from equipment modifier installation and from the client equipment packet route.

## Evidence checked

- Recomputed the r2 report SHA-256; it matches the bound digest above. Compared r1 and r2: the changed paragraph adds the stopLocationBasedEffects/onEquippedItemBroken removal route and distinguishes the entity-break feedback event. No unrelated claim changed.
- Reused the accepted r1 audit for exact unobfuscated source identities, ready/source manifest identities and the unchanged source-file checks.
- Recomputed both LivingEntity.java hashes and matched the version-root source-manifest entries:
  - 26.1.2: c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd
  - 26.2: 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a
- Verified in both versions that stopLocationBasedEffects calls the equipment-slot ItemStack.forEachModifier overload and removes matching AttributeInstance modifiers; onEquippedItemBroken calls stopLocationBasedEffects.
- Verified in both versions that the client equipment event handler invokes breakItem, whose body emits break sound and item particles. That route does not invoke onEquippedItemBroken or stopLocationBasedEffects.
- The ItemStack.forEachModifier call-site search contains the installation collector, the removal method, and tooltip display. The tooltip uses a separate display overload. The correction no longer claims tooltip display is the sole other overload use.
- The report’s main bounded path remains supported: ClientPacketListener.handleSetEquipment stores the stack through LivingEntity.setItemSlot/onEquipItem; onEquipItem does not apply attribute modifiers. The only collectEquipmentChanges call remains within detectEquipmentUpdates, which LivingEntity.tick gates to non-client levels.

## Scope limits

This accepts only the claim that the synchronized client equipment-stack route does not locally install its attribute or enchantment modifiers into the local player's AttributeInstance through the examined methods. It does not close other client initialization/synchronization routes, packaged item values, ordinary Player.stabAttack reachability, gameplay occurrence, or full-pair coverage. No full source-tree rehash, artifact inspection, product/backlog/wiki/Git review, build, test, or runtime check was performed.
