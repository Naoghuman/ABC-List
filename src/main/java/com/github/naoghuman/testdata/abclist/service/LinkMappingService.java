/*
 * Copyright (C) 2017 Naoghuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.testdata.abclist.service;

import com.github.naoghuman.abclist.configuration.IDefaultConfiguration;
import com.github.naoghuman.abclist.model.Link;
import com.github.naoghuman.abclist.model.LinkMapping;
import com.github.naoghuman.abclist.model.LinkMappingType;
import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.testdata.abclist.TestdataGenerator;
import com.github.naoghuman.testdata.abclist.TestdataPresenter;
import com.github.naoghuman.testdata.abclist.view.testdatalinkmapping.TestdataLinkMappingPresenter;
import com.github.naoghuman.lib.database.core.DatabaseFacade;
import com.github.naoghuman.lib.database.core.CrudService;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Naoghuman
 */
public class LinkMappingService extends Service<Void> {
    
    private final DoubleProperty entityProperty = new SimpleDoubleProperty(0.0d);
    
    private int saveMaxEntities = 0;
    
    private TestdataLinkMappingPresenter presenter = null;
    private String entityName = null;
    private String onStartMessage = null;
    
    public LinkMappingService(String entityName) {
        this.entityName = entityName;
    }

    public void bind(TestdataLinkMappingPresenter presenter) {
        this.presenter = presenter;
        
        saveMaxEntities = presenter.getSaveMaxEntities();
        
        entityProperty.unbind();
        entityProperty.setValue(0);
        entityProperty.bind(super.progressProperty());
        
        this.presenter.getProgressBarPercentInformation().textProperty().bind(
                Bindings.createStringBinding(() -> {
                    int process = (int) (entityProperty.getValue() * 100.0d);
                    if (process <= 0) {
                        process = 0;
                    } else {
                        ++process;
                    }

                    return process + "%"; // NOI18N
                },
                entityProperty));
        
        this.presenter.progressPropertyFromEntityDream().unbind();
        this.presenter.progressPropertyFromEntityDream().bind(super.progressProperty());
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            {
                updateProgress(0, saveMaxEntities);
            }
            
            @Override
            protected Void call() throws Exception {
                LoggerFacade.getDefault().deactivate(Boolean.TRUE);
                
                final StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                
                /*
                 1) over all links
                 2) if random > 0.005d then do
                 3) otherwise create a link without parent
                 4) get 1-10 terms, create LinkMapping foreach of them
                     - means a link is mapped to 1-10 terms
                 5) get 0-10 topics, create LinkMapping foreach of them
                     - means a link is mapped to 0-10 topics
                */
                
                final ObservableList<Link> links = SqlProvider.getDefault().findAllLinks();
                final ObservableList<Term> terms = SqlProvider.getDefault().findAllTerms();
                final int sizeTerms = terms.size();
                final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
                final int sizeTopics = topics.size();
                final AtomicInteger index = new AtomicInteger(0);
                
                final CrudService crudService = DatabaseFacade.getDefault().getCrudService(entityName);
                final AtomicLong id = new AtomicLong(-1_000_000_000L + DatabaseFacade.getDefault().getCrudService().count(entityName));
                links.stream() // 1
                        .forEach(link -> {
                            // 2) Should the [Link] have a parent
                            final double random = TestdataGenerator.RANDOM.nextDouble();
                            if (random > 0.005d) {
                                // 4) Create [Link]s with parent [Term]
                                final int maxTerms = TestdataGenerator.RANDOM.nextInt(10) + 1;
                                for (int i = 0; i < maxTerms; i++) {
                                    final LinkMapping lm = ModelProvider.getDefault().getLinkMapping();
                                    lm.setId(id.getAndIncrement());

                                    final Term term = terms.get(TestdataGenerator.RANDOM.nextInt(sizeTerms));
                                    lm.setParentId(term.getId());
                                    lm.setParentType(LinkMappingType.TERM);

                                    lm.setChildId(link.getId());
                                    lm.setChildType(LinkMappingType.LINK);

                                    crudService.create(lm);
                                }

                                // 5) Create [Link]s with parent [Topic]
                                final int maxTopics = TestdataGenerator.RANDOM.nextInt(11);
                                for (int i = 0; i < maxTopics; i++) {
                                    final LinkMapping lm = ModelProvider.getDefault().getLinkMapping();
                                    lm.setId(id.getAndIncrement());

                                    final Topic topic = topics.get(TestdataGenerator.RANDOM.nextInt(sizeTopics));
                                    lm.setParentId(topic.getId());
                                    lm.setParentType(LinkMappingType.TOPIC);

                                    lm.setChildId(link.getId());
                                    lm.setChildType(LinkMappingType.LINK);

                                    crudService.create(lm);
                                }
                            }
                            else {
                                // 3) Some [Link]s havn't a parent
                                final LinkMapping lm = ModelProvider.getDefault().getLinkMapping();
                                lm.setId(id.getAndIncrement());
                                lm.setParentId(IDefaultConfiguration.DEFAULT_ID);
                                lm.setParentType(LinkMappingType.NOT_DEFINED);
                                lm.setChildId(link.getId());
                                lm.setChildType(LinkMappingType.LINK);

                                crudService.create(lm);
                            }
                            
                            updateProgress(index.getAndIncrement(), saveMaxEntities);
                        });
                
                LoggerFacade.getDefault().deactivate(Boolean.FALSE);
                stopWatch.split();
                LoggerFacade.getDefault().debug(this.getClass(), "  + " + stopWatch.toSplitString() + " for " + saveMaxEntities + " LinkMappings."); // NOI18N
		stopWatch.stop();
                
                return null;
            }
        };
    }

    public void setOnStart(String onStartMessage) {
        this.onStartMessage = onStartMessage;
    }
    
    public void setOnSuccededAfterService(TestdataPresenter testdataPresenter, String onSucceededMessage) {
        super.setOnSucceeded((WorkerStateEvent t) -> {
            LoggerFacade.getDefault().debug(this.getClass(), onSucceededMessage);
            
            presenter.setProgressBarInformation(onSucceededMessage);
            
            if (!presenter.getProgressBarPercentInformation().getText().equals("100%")) { // NOI18N
                presenter.getProgressBarPercentInformation().textProperty().unbind();
                presenter.getProgressBarPercentInformation().setText("100%"); // NOI18N
            }
            
            if (testdataPresenter != null) {
                testdataPresenter.cleanUpAfterServices();
            }
        });
    }

    @Override
    public void start() {
        final SequentialTransition sequentialTransition = new SequentialTransition();
        
        final PauseTransition ptProgressBarInformation = new PauseTransition();
        ptProgressBarInformation.setDuration(Duration.millis(250.0d));
        ptProgressBarInformation.setOnFinished((ActionEvent event) -> {
            presenter.setProgressBarInformation(onStartMessage);
        });
        sequentialTransition.getChildren().add(ptProgressBarInformation);
        
        final PauseTransition ptStart = new PauseTransition();
        ptStart.setDuration(Duration.millis(1000.0d));
        ptStart.setOnFinished((ActionEvent event) -> {
            super.start();
        });
        sequentialTransition.getChildren().add(ptStart);
        
        sequentialTransition.playFromStart();
    }
    
}
