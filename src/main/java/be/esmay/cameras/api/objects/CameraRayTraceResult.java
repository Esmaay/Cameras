package be.esmay.cameras.api.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.entity.LivingEntity;

@Getter
@ToString
@RequiredArgsConstructor
public final class CameraRayTraceResult {

    private final LivingEntity hitEntity;

}
