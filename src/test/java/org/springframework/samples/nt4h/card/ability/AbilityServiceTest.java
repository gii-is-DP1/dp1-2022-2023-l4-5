package org.springframework.samples.nt4h.card.ability;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.nt4h.card.ability.*;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.player.Player;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AbilityServiceTest {

    @Mock
    private AbilityRepository abilityRepository;

    @Mock
    private AbilityInGameRepository abilityInGameRepository;

    @Mock
    private AbilityInGame abilityInGame;

    @InjectMocks
    private AbilityService abilityService;

    @Test
    void getAbilityById_whenAbilityExists_returnsAbility() {
        Ability expected = new Ability();
        given(abilityRepository.findById(1)).willReturn(java.util.Optional.of(expected));

        Ability actual = abilityService.getAbilityById(1);

        assertThat(actual).isEqualTo(expected);
        verify(abilityRepository).findById(1);
        verifyNoMoreInteractions(abilityRepository);
    }

    @Test
    void getAbilityById_whenAbilityDoesNotExist_throwsNotFoundException() {
        given(abilityRepository.findById(1)).willReturn(java.util.Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> abilityService.getAbilityById(1))
            .withMessage("Ability not found");
        verify(abilityRepository).findById(1);
        verifyNoMoreInteractions(abilityRepository);
    }

    @Test
    void getAllAbilities_returnsAllAbilities() {
        List<Ability> expected = Arrays.asList(new Ability(), new Ability(), new Ability());
        given(abilityRepository.findAll()).willReturn(expected);

        List<Ability> actual = abilityService.getAllAbilities();
        assertThat(actual).isEqualTo(expected);
        verify(abilityRepository).findAll();
        verifyNoMoreInteractions(abilityRepository);
    }

    @Test
    void getAbilityByName_whenAbilityExists_returnsAbility() {
        Ability expected = new Ability();
        given(abilityRepository.findByName("Ability 1")).willReturn(java.util.Optional.of(expected));

        Ability actual = abilityService.getAbilityByName("Ability 1");

        assertThat(actual).isEqualTo(expected);
        verify(abilityRepository).findByName("Ability 1");
        verifyNoMoreInteractions(abilityRepository);
    }

    @Test
    void getAbilityByName_whenAbilityDoesNotExist_throwsNotFoundException() {
        given(abilityRepository.findByName("Ability 1")).willReturn(java.util.Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> abilityService.getAbilityByName("Ability 1"))
            .withMessage("Ability not found");
        verify(abilityRepository).findByName("Ability 1");
        verifyNoMoreInteractions(abilityRepository);
    }

    @Test
    void abilityExists_whenAbilityExists_returnsTrue() {
        given(abilityRepository.existsById(1)).willReturn(true);

        boolean result = abilityService.abilityExists(1);

        assertThat(result).isTrue();
        verify(abilityRepository).existsById(1);
        verifyNoMoreInteractions(abilityRepository);
    }

    @Test
    void abilityExists_whenAbilityDoesNotExist_returnsFalse() {
        given(abilityRepository.existsById(1)).willReturn(false);
        boolean result = abilityService.abilityExists(1);
        assertThat(result).isFalse();
        verify(abilityRepository).existsById(1);
        verifyNoMoreInteractions(abilityRepository);
    }

    @Test
    void getAbilityInGameById_whenAbilityInGameExists_returnsAbilityInGame() {
        AbilityInGame expected = new AbilityInGame();
        given(abilityInGameRepository.findById(1)).willReturn(java.util.Optional.of(expected));

        AbilityInGame actual = abilityService.getAbilityInGameById(1);

        assertThat(actual).isEqualTo(expected);
        verify(abilityInGameRepository).findById(1);
        verifyNoMoreInteractions(abilityInGameRepository);
    }

    @Test
    void getAbilityInGameById_whenAbilityInGameDoesNotExist_throwsNotFoundException() {
        given(abilityInGameRepository.findById(1)).willReturn(java.util.Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> abilityService.getAbilityInGameById(1))
            .withMessage("AbilityInGame not found");
        verify(abilityInGameRepository).findById(1);
        verifyNoMoreInteractions(abilityInGameRepository);
    }

    @Test
    void getAllAbilityInGame_returnsAllAbilityInGame() {
        List<AbilityInGame> expected = Arrays.asList(new AbilityInGame(), new AbilityInGame(), new AbilityInGame());
        given(abilityInGameRepository.findAll()).willReturn(expected);

        List<AbilityInGame> actual = abilityService.getAllAbilityInGame();

        assertThat(actual).isEqualTo(expected);
        verify(abilityInGameRepository).findAll();
        verifyNoMoreInteractions(abilityInGameRepository);
    }

    @Test
    void getAbilitiesByRole_returnsAbilities() {
        Role role = Role.KNIGHT;
        List<Ability> expected = Arrays.asList(new Ability(), new Ability(), new Ability());
        given(abilityRepository.findAllByIds(role.getAbilities())).willReturn(expected);

        List<Ability> actual = abilityService.getAbilitiesByRole(role);

        assertThat(actual).isEqualTo(expected);
        verify(abilityRepository).findAllByIds(role.getAbilities());
        verifyNoMoreInteractions(abilityRepository);
    }

    @Test
    void saveAbilityInGame_savesAbilityInGame() {
        AbilityInGame abilityInGame = new AbilityInGame();

        abilityService.saveAbilityInGame(abilityInGame);

        verify(abilityInGameRepository).save(abilityInGame);
        verifyNoMoreInteractions(abilityInGameRepository);
    }

    @Test
    void deleteAbilityInGame_deletesAbilityInGame() {
        abilityService.deleteAbilityInGame(abilityInGame);

        verify(abilityInGame).onDeleteSetNull();
        verify(abilityInGameRepository).save(abilityInGame);
        verify(abilityInGameRepository).delete(abilityInGame);
        verifyNoMoreInteractions(abilityInGameRepository);
    }

    @Test
    void deleteAbilityInGameById_deletesAbilityInGameById() {
        given(abilityInGameRepository.findById(1)).willReturn(java.util.Optional.of(abilityInGame));

        abilityService.deleteAbilityInGameById(1);

        verify(abilityInGame).onDeleteSetNull();
        verify(abilityInGameRepository).save(abilityInGame);
        verify(abilityInGameRepository).delete(abilityInGame);
        verifyNoMoreInteractions(abilityInGameRepository);
    }

    @Test
    void deleteAllAbilityInGame_deletesAllAbilityInGame() {
        List<AbilityInGame> abilityInGame = Arrays.asList(new AbilityInGame(), new AbilityInGame(), new AbilityInGame());

        abilityService.deleteAllAbilityInGame(abilityInGame);

        verify(abilityInGameRepository).deleteAll(abilityInGame);
        verifyNoMoreInteractions(abilityInGameRepository);
    }

    @Test
    void abilityInGameExists_whenAbilityInGameExists_returnsTrue() {
        given(abilityInGameRepository.existsById(1)).willReturn(true);

        boolean result = abilityService.abilityInGameExists(1);

        assertThat(result).isTrue();
        verify(abilityInGameRepository).existsById(1);
        verifyNoMoreInteractions(abilityInGameRepository);
    }

    @Test
    void abilityInGameExists_whenAbilityInGameDoesNotExist_returnsFalse() {
        given(abilityInGameRepository.existsById(1)).willReturn(false);

        boolean result = abilityService.abilityInGameExists(1);

        assertThat(result).isFalse();
        verify(abilityInGameRepository).existsById(1);
        verifyNoMoreInteractions(abilityInGameRepository);
    }
}

