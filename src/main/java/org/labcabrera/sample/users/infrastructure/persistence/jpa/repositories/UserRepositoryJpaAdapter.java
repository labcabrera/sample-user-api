package org.labcabrera.sample.users.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.users.application.ports.UserRepository;
import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.infrastructure.persistence.jpa.entities.UserEntity;
import org.labcabrera.sample.users.infrastructure.persistence.jpa.mappers.UserEntityMapper;
import org.labcabrera.sample.users.shared.application.SecurityPort.AuthenticatedUser;
import org.labcabrera.sample.users.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.users.shared.infrastructure.persistence.rsql.CustomRsqlVisitor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@SuppressWarnings("null")
public class UserRepositoryJpaAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserEntityMapper mapper;
    private final RSQLParser rsqlParser;

    @Override
    public Optional<User> findById(String caseFolderId) {
        return jpaRepository.findById(caseFolderId).map(mapper::toDomain);
    }

    @Override
    public Page<User> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user) {
        if (StringUtils.isBlank(rsql)) {
            var page = jpaRepository.findAll(pageable);
            return page.map(mapper::toDomain);
        }
        try {
            Node rootNode = rsqlParser.parse(rsql);
            Specification<UserEntity> spec = rootNode.accept(new CustomRsqlVisitor<UserEntity>());
            var page = jpaRepository.findAll(spec, pageable);
            return page.map(mapper::toDomain);
        }
        catch (Exception ex) {
            throw new BadRequestException("rsql.msg.err.parse", ex, rsql);
        }
    }

    @Override
    @Transactional
    public User save(User caseFolder) {
        try {
            if (caseFolder.getId() != null && jpaRepository.existsById(caseFolder.getId())) {
                throw new BadRequestException("case-folder.msg.err.already-exists", caseFolder.getId());
            }
            var entity = mapper.toEntity(caseFolder);
            var savedEntity = jpaRepository.save(entity);
            return mapper.toDomain(savedEntity);
        }
        catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("case-folder.msg.err.data-integrity", ex);
        }
    }

    @Override
    @Transactional
    public User update(String userId, User caseFolder) {
        var current = jpaRepository.findById(caseFolder.getId())
            .orElseThrow(() -> new BadRequestException("Case folder not found with id " + caseFolder.getId()));
        current.setName(caseFolder.getName());
        var savedEntity = jpaRepository.save(current);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public void deleteById(String caseFolderId) {
        jpaRepository.deleteById(caseFolderId);
    }

}
